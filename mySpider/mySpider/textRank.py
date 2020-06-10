#!/usr/bin/python3
# -*- coding: utf-8 -*-

"""
@author: JY
@project: Treefrog
@file: textRank.py
@function: 分类
@time: 2020-5-2 12:15
"""
import pathlib
import re
import time
import random
# import sys,os
# sys.path.append(os.path.dirname(__file__) + os.sep + '../')
import jieba
from jieba.analyse import *
import os


def EnumPathFiles(path, callback):
    if not os.path.isdir(path):
        print('Error:"', path, '" is not a directory or does not exist.')
        return
    list_dirs = os.walk(path)
    for root, dirs, files in list_dirs:
        for d in dirs:
            EnumPathFiles(os.path.join(root, d), callback)  # 递归遍历所有子文件夹
        for f in files:
            callback(root, f)


def callback1(path, filename):
    # print(path+'/'+filename)
    # dir_path = '../txt'
    # dir_path = './treefrogFile'
    dir_path = './Treefrog/upload'
    txt_path = dir_path + '/data.txt'
    if not pathlib.Path(dir_path).exists():
        # 根据路径创建文件夹, parents = True时，会依次创建路径中间缺少的文件夹
        pathlib.Path(dir_path).mkdir(parents=True)
        time.sleep(0.01)
    if not pathlib.Path(txt_path).exists():
        with open(txt_path, 'w', encoding='utf-8') as f:
            f.write('\n')
    with open(txt_path, 'r', encoding='utf-8') as f:
        data_content = f.read().replace('\n', '')

    data = ''.join(re.findall('.*?txt', filename)).split(".txt")[0].replace('\n', '')
    # print(data)
    # time.sleep(0.005)
    if data is not None and data not in str(data_content):
        # if data not in str(data_content):
        with open(txt_path, 'a', encoding='utf-8') as f:
            f.write(data.replace('\n', ''))
            print("-" * 30 + "成功写入data.txt" + "-" * 30 + "\n")


def get_category(file_name, content):
    # EnumPathFiles('../txt', callback1)
    EnumPathFiles('./download', callback1)
    # EnumPathFiles('../download/txt/初中/初中化学/九年级化学', callback1)
    # time.sleep(0.3)
    # dir_path = '../txt'
    # 存放标签的文件夹的路径
    # dir_path = './treefrogFile'
    dir_path = './Treefrog/upload'
    txt_path = dir_path + '/data.txt'
    with open(txt_path, 'r', encoding='utf-8') as f:
        data = f.read().replace('\n', '')
        # data = data.replace('\n', '')
        print(data)
    cate = []
    for keyword, weight in textrank(data, topK=100, withWeight=True):
        # for keyword, weight in extract_tags(data, topK=20, withWeight=True):
        print('%s %s' % (keyword, weight))
        # print(type(keyword))
        cate.append(keyword)
    print(cate)
    tags = []
    tags_path = dir_path + '/../tags'
    # 根据分类 创建文件夹
    # for i in cate:
    #     cate_path = tags_path + '/' + i
    #     if not pathlib.Path(cate_path).exists():
    #         pathlib.Path(cate_path).mkdir(parents=True)
    #     # time.sleep(0.01)
    print("+" * 52)
    for i in cate:
        if i in file_name:
            small_dir_path = tags_path + '/' + i + '/' + file_name
            if not pathlib.Path(small_dir_path).exists():
                # 根据路径创建文件夹, parents = True时，会依次创建路径中间缺少的文件夹
                pathlib.Path(small_dir_path).mkdir(parents=True)
            text_path = small_dir_path + '/' + file_name + '.txt'
            p = pathlib.Path(text_path)
            if not p.exists():
                with open(text_path, 'w', encoding="utf-8") as f:
                    f.write(file_name + "\n")
                    f.write(content)
            tags.append(i)
    print("-" * 10 + "文件下载完成" + "-" * 30)
    print("+" * 52)
    if len(tags):
        tag = random.choice(tags)
    else:
        tag = ''
    print('#' * 50)
    print(tags)
    print('#' * 50)
    print(tag)
    print('#' * 50)
    # else:
    #     print("-" * 10 + "该文件已下载" + "-" * 30)

    return tag


if __name__ == '__main__':
    get_category('九年级上册化学第一单元走进化学世界考点总结', '九年级上册化学第一单元走进化学世界考点总结')
