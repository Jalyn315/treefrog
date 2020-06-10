#!/usr/bin/python3
# -*- coding: utf-8 -*-

import subprocess
import time
import os


CYCLE_TIME = 30

spiders = ['cc518', 'crawldb']

cmd = 'scrapy crawl {}'
i = 0
while True:
    for s in spiders:
        subprocess.Popen(cmd.format(s), shell=True if os.name == 'posix' else False)
    i += 1
    print("第{}轮执行".format(i))
    time.sleep(CYCLE_TIME)

# os.name 在windows上是nt, linux上是posix
# 启动命令可以采用nohup python -u start.py &