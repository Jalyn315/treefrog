#!/usr/bin/python3
# -*- coding: utf-8 -*-

"""
@author: JY
@project: Treefrog
@file: mycrawl.py.py
@function: 在cmd中输入 scrapy mycrawl替换掉以前的scrapy crawl <爬虫名>。实现多个爬虫同时运行
@time: 2020-3-7 11:43
"""
import os
import time

from scrapy.commands import ScrapyCommand
from scrapy.utils.conf import arglist_to_dict
from scrapy.utils.python import without_none_values
from scrapy.exceptions import UsageError


class Command(ScrapyCommand):
    requires_project = True

    def syntax(self):
        return "[options] <spider>"

    def short_desc(self):
        return "Run a spider"

    def add_options(self, parser):
        ScrapyCommand.add_options(self, parser)
        parser.add_option("-a", dest="spargs", action="append", default=[], metavar="NAME=VALUE",
                          help="set spider argument (may be repeated)")
        parser.add_option("-o", "--output", metavar="FILE",
                          help="dump scraped items into FILE (use - for stdout)")
        parser.add_option("-t", "--output-format", metavar="FORMAT",
                          help="format to use for dumping items with -o")

    def process_options(self, args, opts):
        ScrapyCommand.process_options(self, args, opts)
        try:
            opts.spargs = arglist_to_dict(opts.spargs)
        except ValueError:
            raise UsageError("Invalid -a value, use -a NAME=VALUE", print_help=False)
        if opts.output:
            if opts.output == '-':
                self.settings.set('FEED_URI', 'stdout:', priority='cmdline')
            else:
                self.settings.set('FEED_URI', opts.output, priority='cmdline')
            feed_exporters = without_none_values(
                self.settings.getwithbase('FEED_EXPORTERS'))
            valid_output_formats = feed_exporters.keys()
            if not opts.output_format:
                opts.output_format = os.path.splitext(opts.output)[1].replace(".", "")
            if opts.output_format not in valid_output_formats:
                raise UsageError("Unrecognized output format '%s', set one"
                                 " using the '-t' switch or as a file extension"
                                 " from the supported list %s" % (opts.output_format,
                                                                  tuple(valid_output_formats)))
            self.settings.set('FEED_FORMAT', opts.output_format, priority='cmdline')

    def run(self, args, opts):
        # 修改后：
        # 获取爬虫列表
        spd_loader_list = self.crawler_process.spider_loader.list()
        print(spd_loader_list)
        # 遍历各爬虫
        for spname in spd_loader_list or args:
            self.crawler_process.crawl(spname, **opts.spargs)
            print("\n" + "\t\t此时启动的爬虫：" + spname + "\n\n")
        self.crawler_process.start()
            # time.sleep(3)

        # 源码：
        # if len(args) < 1:
        #     raise UsageError()
        # elif len(args) > 1:
        #     raise UsageError("running 'scrapy crawl' with more than one spider is no longer supported")
        # spname = args[0]
        #
        # crawl_defer = self.crawler_process.crawl(spname, **opts.spargs)

        # if getattr(crawl_defer, 'result', None) is not None and issubclass(crawl_defer.result.type, Exception):
        #     self.exitcode = 1
        # else:
        #     self.crawler_process.start()
        #
        #     if self.crawler_process.bootstrap_failed or \
        #             (hasattr(self.crawler_process, 'has_exception') and self.crawler_process.has_exception):
        #         self.exitcode = 1
