# -*- coding: utf-8 -*-

# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: https://doc.scrapy.org/en/latest/topics/item-pipeline.html

import json
import pathlib
import codecs
import re
import socket
import time
import urllib.request
import pymysql

from .file_to_zip import zip_file_path
from .items import MyspiderItem
from .spiders.cc518 import Cc518Spider
from .spiders.crawldb import CrawldbSpider
from scrapy.utils.project import get_project_settings
# import sys,os
# sys.path.append(os.path.dirname(__file__) + os.sep + '../')
from .textRank import get_category


class MyspiderPipeline(object):
    '''
        一个pipeline demo
    '''

    def process_item(self, item, spider):
        # 根据不同的item，处理不同的逻辑
        if isinstance(item, MyspiderItem):  # 判断当前item是否为MyspiderItem类型
            pass
        # elif isinstance(item, cc518Item):  # 判断当前item是否为cc518Item类型
        #     pass

        # 根据不同的爬虫名字，处理不同的逻辑
        if isinstance(spider, CrawldbSpider):  # 判断spider是否为CrawldbSpider类型
            # if spider.name == "crawldb":  # 判断spider是否为crawldb类型
            pass
        elif isinstance(spider, Cc518Spider):  # 判断当前spider是否为Cc518Spider类型
            # elif spider.name == "cc518":
            pass
        return item


# 显示下载文件的进度
def callback(a1, a2, a3):
    """
        @a1:目前为此传递的数据块数量
        @a2:每个数据块的大小，单位是byte,字节
        @a3:远程文件的大小
    """
    download_pg = 100.0 * a1 * a2 / a3
    if download_pg > 100:
        download_pg = 100
    print("%.2f%%" % download_pg, )


def download(url, filename, callback, header):
    """
    封装了 urlretrieve()的自定义函数，递归调用urlretrieve(),当下载失败时，重新下载
    download file from internet
    :param url: path to download from
    :param filename: path to save files
    :return: None
    """

    try:
        urllib.request.urlretrieve(url, filename, callback, header)
    except Exception as e:
        print(e)
        print('Network conditions is not good.\nReloading.....')
        download(url, filename, callback, header)


# 爬虫：crawldb 的pipeline
class EncodingTxtPipeline(object):
    '''
        将MySQL数据库中的数据保存到本地
    '''

    def __init__(self, connect):
        self.connect = connect
        self.cursor = self.connect.cursor()
        self.header = get_project_settings().get("USER_AGENT")

    @classmethod
    def from_settings(cls, settings):
        try:
            connect = pymysql.connect(host=settings['MYSQL_HOST'],
                                      port=settings['MYSQL_PORT'],
                                      user=settings['MYSQL_USER'],
                                      passwd=settings['MYSQL_PASSWORD'],
                                      db=settings['MYSQL_DBNAME'],
                                      charset='utf8',
                                      cursorclass=pymysql.cursors.SSCursor)  # pymysql.cursors.SSCursor设置游标为无缓冲元组类型
        except Exception as e:
            print(e, '\nMysql connect error!')
        return cls(connect)

    def process_item(self, item, spider):
        if isinstance(item, MyspiderItem):
            # if isinstance(item, cc518Item):
            if isinstance(spider, CrawldbSpider):
                # if isinstance(spider, Cc518Spider):
                self.cursor.execute("select article_title from article where article_title IS NOT NULL ")
                all_data = self.cursor.fetchall()  # 实现用fetchall（）
                # print(all_data)
                # 将内容content保存到文件中
                for data in all_data:
                    print(data)
                    # print(data[0])  # data[0] 就是 all_data里面的第一个 article_title 元素
                    sql = "select article_title, article_tags, article_content, big_category, small_category from article where article_title=%s"
                    para = str(data[0])
                    self.cursor.execute(sql, para)
                    time.sleep(0.01)
                    one_data = self.cursor.fetchone()  # fetchone为一位元组
                    # print("!" * 50)
                    # print(one_data)
                    # print("!" * 50)
                    # 将文本下载保存到本地
                    file_name = str(one_data[0])  # fetchone()
                    file_name.replace('|', ',').replace('/', ',')
                    print(file_name)
                    print('*' * 50)
                    print('*' * 50)
                    tag_name = str(one_data[1])  # fetchone()
                    print(tag_name)
                    print('*' * 40)
                    print('*' * 40)

                    content = str(one_data[2])  # fetchone()

                    big_dir_name = str(one_data[3])  # fetchone()
                    print(big_dir_name)
                    print('*' * 30)
                    print('*' * 30)
                    small_dir_name = str(one_data[4])  # fetchone()
                    print(small_dir_name)
                    print('*' * 20)
                    print('*' * 20)
                    # 检查异常，解决路径出错问题，路径有特殊符号，则跳过当前循环。
                    try:
                        # folder_path = './treefrogFile'
                        folder_path = './Treefrog/upload'
                        data_path = folder_path + '/' + 'data.txt'
                        # 存放数据文件压缩包的路径
                        src_path = folder_path + '/' + file_name
                        save_path = src_path.split('.', 1)[1]
                        print("save_path:" + save_path)
                        if not pathlib.Path(folder_path).exists():
                            pathlib.Path(folder_path).mkdir(parents=True)
                            time.sleep(0.01)
                        if not pathlib.Path(src_path).exists():
                            pathlib.Path(src_path).mkdir(parents=True)
                            time.sleep(0.01)
                        if not pathlib.Path(data_path).exists():
                            # 创建一个空的data.txt文件，为后面的提取关键字操作做准备。
                            with open(data_path, 'w', encoding="utf-8") as f:
                                f.write('\n')
                        # 文本标签分类文件夹路径
                        txt_file_path = './download/' + file_name
                        if not pathlib.Path(txt_file_path).exists():
                            pathlib.Path(txt_file_path).mkdir(parents=True)
                            time.sleep(0.01)
                        text_path = txt_file_path + '/' + file_name + '.txt'
                        p = pathlib.Path(text_path)
                        if not p.exists():
                            with open(text_path, 'w', encoding="utf-8") as f:
                                print("+" * 50)
                                f.write(file_name + "\n")
                                f.write(content)
                                print("-" * 10 + "文件下载完成" + "-" * 30)
                                print("+" * 50)
                        else:
                            print("-" * 10 + "该文件已下载" + "-" * 30 + "\n")
                    except Exception as e:
                        print(e)
                        continue
                    # 获取当前数据的分类标签
                    tag = get_category(file_name, one_data[2])
                    item['tag'] = tag
                    # cate,dir_path = get_category()
                    # self.mk_category_dir(cate,dir_path,file_name,one_data[2])
                    # 判断文本内容one_data[2]是否有 图片链接，有，则提取出来并下载
                    get_img_url(self, txt_file_path, file_name, one_data[2])
                    # 对文件进行压缩处理
                    zip_path, zip_size = zip_file_path(txt_file_path, src_path, file_name + '.zip')
                    print(zip_path, zip_size)
                    item["name"] = zip_path.split('/')[4]
                    print("/" * 40)
                    print(item["name"])
                    print("/" * 40)
                    item["suffix"] = item["name"].split('.')[1]
                    item["size"] = zip_size
                    item['local_url'] = save_path + '/'
                    item["user_id"] = 2
                    item["category_id"] = 0
                    item["is_downloadable"] = 0
                    item["is_uploadable"] = 0
                    item["is_visible"] = 0
                    item["is_deletable"] = 0
                    item["is_updatable"] = 0
                    time.sleep(0.01)
                    self.set_table_file(item)
                    self.set_table_type(item)
                    continue
                    return item
            else:
                # print中输出字符串的三种方式
                print('-' * 50 + "该爬虫(%s) 无任何操作,请关闭爬虫(%s)......" % (spider.name, spider.name) + '-' * 50)
                return item

    def set_table_file(self, item):
        '''
        set table file
        '''
        self.cursor.execute("select * from file where name = %s", item["name"])
        print("++++++++++++++++")
        print(item["name"])
        # print(type(item["name"]))
        print("++++++++++++++++")
        data = self.cursor.fetchone()
        print("-" * 50)
        print("-" * 50)
        print(data)
        print("-" * 50)
        print("-" * 50)
        try:
            if data is not None:
                do_update(self, item)
                print("-" * 30 + "在 file.sql 中更新了一条数据" + "-" * 30 + "\n")
            else:
                do_insert(self, item)
                print("-" * 30 + "在 file.sql 中插入了一条数据" + "-" * 30 + "\n")
        except Exception as error:
            print("-" * 50)
            print(error)
            print("_" * 50)
            print("------错误------" + "\n")

    def set_table_type(self, item):
        '''
        set table type
        '''
        self.cursor.execute("select * from type where type_name = %s", item["tag"])
        print("++++++++++++++++")
        print(item["tag"])
        # print(type(item["tag"]))
        print("++++++++++++++++")
        data = self.cursor.fetchone()
        print("-" * 50)
        print("-" * 50)
        print(data)
        print("-" * 50)
        print("-" * 50)
        try:
            if data is None:
                # 数据库插入操作，向 tag 表中插入数据
                self.cursor.execute("insert into type(type_name) values(%s)", item["tag"])
                self.connect.commit()
                print("-" * 30 + "在 type.sql 中插入了一条数据" + "-" * 30 + "\n")
        except Exception as error:
            print("+" * 50)
            print(error)
            print("_" * 50)
            print("------错误------" + "\n")

    def close_spider(self, spider):
        self.cursor.close()
        self.connect.close()


def get_img_url(self, file_path, file_name, data):
    img_urls = re.findall('http.*?jpg', data)
    print(img_urls)
    index = 1
    for img_src in img_urls:
        img_path = file_path + '/' + file_name + '{}.jpg'.format(index)
        index += 1
        i = pathlib.Path(img_path)
        if not i.exists():
            print("\n")
            print('Downloading data from %s' % img_src)
            time.sleep(0.01)  # 设置的缓冲时间，个人习惯
            # 设置超时时间为10s,时间长短自定义
            socket.setdefaulttimeout(10)
            # 解决下载不完全问题且避免陷入死循环
            try:
                download(img_src, img_path, callback, self.header.encode())
                print(img_path, '\nDownload finished!')
            except socket.timeout:
                count = 1
                while count <= 5:
                    try:
                        download(img_src, img_path, callback, self.header.encode())  # 封装了 urlretrieve()的自定义函数，递归调用urlretrieve
                        break
                    except socket.timeout:
                        err_info = 'Reloading for %d time' % count if count == 1 else 'Reloading for %d times' % count
                        print(err_info)
                        count += 1
                if count > 5:
                    print("downloading picture fialed!")
        else:
            print("\n")
            print(img_path, "\n'Img_file already exsits!'")
        # 获取文件大小
        filesize = i.stat().st_size
        # 文件大小默认以Bytes计， 转换为Mb
        print('Img_file size = %.2f Mb(%.2f Kb)' % (filesize / 1024 / 1024, filesize / 1024))


def do_insert(self, item):
    '''
    数据库插入操作，向 file 表中插入数据
    '''
    insert_sql = "insert into file(name, suffix, local_url, size, tag, user_id, category_id, is_downloadable, is_uploadable, is_visible, is_deletable, is_updatable)\
                 values(%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)"
    params = (item["name"], item["suffix"], item["local_url"], item["size"], item["tag"], item["user_id"], item["category_id"], item["is_downloadable"],
              item["is_uploadable"], item["is_visible"], item["is_deletable"], item["is_updatable"])
    self.cursor.execute(insert_sql, params)
    self.connect.commit()


def do_update(self, item):
    '''
    数据库更新操作
    '''
    update_sql = "update file set name = %s, suffix = %s, local_url = %s, size = %s, tag = %s, user_id = %s, category_id=%s,\
                                    is_downloadable = %s, is_uploadable = %s, is_visible = %s, is_deletable = %s, is_updatable = %s where name = %s"
    params = (item["name"], item["suffix"], item["local_url"], item["size"], item["tag"], item["user_id"], item["category_id"], item["is_downloadable"],
              item["is_uploadable"], item["is_visible"], item["is_deletable"], item["is_updatable"], item["name"])
    self.cursor.execute(update_sql, params)
    self.connect.commit()


# 爬虫：cc518 的pipeline
class MySQLPipeline(object):
    '''
        将获取到的数据保存到MySQL数据库中
    '''

    def __init__(self, connect):
        self.connect = connect
        self.cursor = self.connect.cursor()
        # dispatcher.connect(self.spider_closed, signals.spider_closed)

    @classmethod
    def from_settings(cls, settings):
        connect = pymysql.connect(host=settings['MYSQL_HOST'],
                                  port=settings['MYSQL_PORT'],
                                  user=settings['MYSQL_USER'],
                                  passwd=settings['MYSQL_PASSWORD'],
                                  db=settings['MYSQL_DBNAME'],
                                  charset='utf8'
                                  )
        return cls(connect)

    def process_item(self, item, spider):
        if isinstance(item, MyspiderItem):
            if isinstance(spider, Cc518Spider):
                self.cursor.execute("select * from article where article_title= %s", item["article_title"])
                all_data = self.cursor.fetchall()
                print("-" * 50)
                print("-" * 50)
                print(type(all_data))
                print(all_data)
                # print(all_data[0])
                print("-" * 50)
                print("-" * 50)
                try:
                    # 判断元组为空。 如果 all_data 为空元组，则下面语句为：如果不为空（即为true）时，执行if的内容
                    if not all_data:
                        # if len(all_data) == 0:
                        self.do_insert(item)
                        print("-" * 30 + "插入了一条数据" + "-" * 30 + "\n")
                    else:
                        for data in all_data:
                            print("+" * 50)
                            print(type(data))
                            print(data)
                            print("+" * 50)
                            try:
                                if item["article_url"] == data[5]:
                                    self.do_update(item)
                                    print("-" * 30 + "更新了一条数据" + "-" * 30 + "\n")
                                # elif not data:
                                else:
                                    self.do_insert(item)
                                    print("-" * 30 + "插入了一条数据" + "-" * 30 + "\n")
                            except Exception as error:
                                print("-" * 50)
                                print(error)
                                print("_" * 50)
                                print("------错误------" + "\n")
                except Exception as error:
                    print("-" * 50)
                    print(error)
                    print("_" * 50)
                    print("------错误------" + "\n")
                return item

    def do_insert(self, item):
        '''
        数据库插入操作
        '''
        insert_sql = "insert into article(big_category, bc_href, small_category, sc_href, article_title, article_url, " \
                     "article_tags, article_content) values(%s, %s, %s, %s, %s, %s, %s, %s)"
        params = (item["big_category"], item["bc_url"], item["small_category"], item["sc_url"], item["article_title"], item["article_url"], item["article_tags"],
                  item["article_content"])
        self.cursor.execute(insert_sql, params)
        self.connect.commit()

    def do_update(self, item):
        '''
        数据库更新操作
        '''
        update_sql = "update article set big_category = %s,bc_href = %s,small_category = %s,sc_href = %s,\
                                article_title = %s,article_url = %s,article_tags = %s,article_content =%s where article_url  = %s"
        params = (item["big_category"], item["bc_url"], item["small_category"], item["sc_url"], item["article_title"], item["article_url"], item["article_tags"],
                  item["article_content"], item["article_url"])
        self.cursor.execute(update_sql, params)
        self.connect.commit()

    def close_spider(self, spider):
        self.cursor.close()
        self.connect.close()
        print("\n 爬虫已关闭...")


# 爬虫：cc518 的pipeline
class JsonEncodingPipeline(object):
    def open_spider(self, spider):
        self.file = codecs.open("article.json", 'w', encoding='utf-8')
        self.file.write('[')

    def process_item(self, item, spider):
        """
        ensure_ascii=False 防止中文等编码错误
        """
        if isinstance(item, MyspiderItem):
            # if spider.name == "cc518":
            if isinstance(spider, Cc518Spider):
                lines = json.dumps(dict(item), ensure_ascii=False) + ',\n'
                self.file.write(lines)
                return item
            else:
                print('-' * 50 + "该爬虫无法进行 JsonEncoding 操作" + '-' * 50)
                self.close_spider()

    def close_spider(self, spider):
        """
        内置方法
        spider_close(signal) 关闭文件
        """
        self.file.write(']')
        self.file.close()
