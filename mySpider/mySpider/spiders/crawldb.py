# -*- coding: utf-8 -*-
import scrapy
from ..items import MyspiderItem


class CrawldbSpider(scrapy.Spider):
    name = 'crawldb'
    allowed_domains = ['www.cc518.com']
    start_urls = ['http://www.cc518.com/']
    # spider中设置管道
    custom_settings = {
        'ITEM_PIPELINES': {
            'mySpider.items.MyspiderItem': 2,
            'mySpider.pipelines.EncodingTxtPipeline': 102# 从数据库写入文本中
        }
    }

    def parse(self, response):
        '''
            任意传一个item到PipelineMysqlToTxt，实现将数据库保存到本地txt操作
        '''
        yield MyspiderItem()
