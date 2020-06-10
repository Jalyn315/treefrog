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
    # cmdline.execute("scrapy mycrawl".split())
    cmdline.execute("scrapy crawl crawldb".split())