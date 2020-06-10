#!/usr/bin/python3
# -*- coding: utf-8 -*-

"""
@author: JY
@project: Treefrog
@file: start_in_win.py
@function:
@time: 2020-4-13 15:51
"""
from scrapy import cmdline

if __name__ == '__main__':
    # 同时执行两个爬虫.py文件
    cmdline.execute("scrapy mycrawl".split())
    # 执行爬虫 crawldb
    # cmdline.execute("scrapy crawl crawldb".split())