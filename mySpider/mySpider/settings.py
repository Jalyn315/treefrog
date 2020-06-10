# -*- coding: utf-8 -*-

# Scrapy settings for mySpider project
#
# For simplicity, this file contains only settings considered important or
# commonly used. You can find more settings consulting the documentation:
#
#     https://doc.scrapy.org/en/latest/topics/settings.html
#     https://doc.scrapy.org/en/latest/topics/downloader-middleware.html
#     https://doc.scrapy.org/en/latest/topics/spider-middleware.html

BOT_NAME = 'mySpider'

SPIDER_MODULES = ['mySpider.spiders']
NEWSPIDER_MODULE = 'mySpider.spiders'
# 尝试配置同时启动多个爬虫：
# 可以在项目配置文件(setting.py)中进行相应配置，格式:"COMMANDS_MODULE='项目核心目录.自定义命令源码目录'"，
# 随后，在命令行中进入该项目所在目录，并输入scrapy -h,可以查看是否有命令mycrawl 。如果有，就可以使用自定义命令mycrawl启动所有爬虫文件了。
COMMANDS_MODULE = 'mySpider.mycmd'


# LOG_LEVEL = "WARNING"   # 过滤 WARNING以下的 debug提示信息
LOG_FILE = "./log.log"  # 保存运行日志到 当前目录下的log.log文件中

# Crawl responsibly by identifying yourself (and your website) on the user-agent
USER_AGENT = 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.80 Safari/537.36'


# Obey robots.txt rules
ROBOTSTXT_OBEY = False

# Configure maximum concurrent requests performed by Scrapy (default: 16)
# CONCURRENT_REQUESTS = 32

# 关闭spider的情况可以通过下面的设置项配置：
# CLOSESPIDER_TIMEOUT = 0
# CLOSESPIDER_TIMEOUT = 10

# CLOSESPIDER_PAGECOUNT = 0
# CLOSESPIDER_PAGECOUNT = 10
# CLOSESPIDER_ITEMCOUNT = 0
# CLOSESPIDER_ERRORCOUNT = 0

# 爬虫运行超过23.5小时(84600秒），如果爬虫还没有结束，则自动关闭
# CLOSESPIDER_TIMEOUT = 84600   #

# Configure a delay for requests for the same website (default: 0)
# See https://doc.scrapy.org/en/latest/topics/settings.html#download-delay
# See also autothrottle settings and docs
DOWNLOAD_DELAY = 0.1
# The download delay setting will honor only one of:
#CONCURRENT_REQUESTS_PER_DOMAIN = 16
#CONCURRENT_REQUESTS_PER_IP = 16

# Disable cookies (enabled by default)
#COOKIES_ENABLED = False

# Disable Telnet Console (enabled by default)
#TELNETCONSOLE_ENABLED = False

# Override the default request headers:
#DEFAULT_REQUEST_HEADERS = {
#   'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8',
#   'Accept-Language': 'en',
#}

# Enable or disable spider middlewares
# See https://doc.scrapy.org/en/latest/topics/spider-middleware.html
SPIDER_MIDDLEWARES = {
   'mySpider.middlewares.MyspiderSpiderMiddleware': 543,
}

# Enable or disable downloader middlewares
# See https://doc.scrapy.org/en/latest/topics/downloader-middleware.html
DOWNLOADER_MIDDLEWARES = {
   'mySpider.middlewares.MyspiderDownloaderMiddleware': 543,
}

# Enable or disable extensions
# See https://doc.scrapy.org/en/latest/topics/extensions.html
#EXTENSIONS = {
#    'scrapy.extensions.telnet.TelnetConsole': None,
#}

# Configure item pipelines
# See https://doc.scrapy.org/en/latest/topics/item-pipeline.html

# 已在各个spider.py中设置了 ITEM_PIPELINES
# ITEM_PIPELINES = {
#    'mySpider.items.cc518Item': 1,
#    'mySpider.items.MyspiderItem': 2,
#    # 'mySpider.pipelines.MyspiderPipeline': 300,
#    'mySpider.pipelines.MySQLPipeline': 102,
#    'mySpider.pipelines.JsonEncodingPipeline': 101,
#    'mySpider.pipelines.MysqlToTxtPipeline': 100,
# }

# Enable and configure the AutoThrottle extension (disabled by default)
# See https://doc.scrapy.org/en/latest/topics/autothrottle.html
#AUTOTHROTTLE_ENABLED = True
# The initial download delay
#AUTOTHROTTLE_START_DELAY = 5
# The maximum download delay to be set in case of high latencies
#AUTOTHROTTLE_MAX_DELAY = 60
# The average number of requests Scrapy should be sending in parallel to
# each remote server
#AUTOTHROTTLE_TARGET_CONCURRENCY = 1.0
# Enable showing throttling stats for every response received:
#AUTOTHROTTLE_DEBUG = False

# Enable and configure HTTP caching (disabled by default)
# See https://doc.scrapy.org/en/latest/topics/downloader-middleware.html#httpcache-middleware-settings
#HTTPCACHE_ENABLED = True
#HTTPCACHE_EXPIRATION_SECS = 0
#HTTPCACHE_DIR = 'httpcache'
#HTTPCACHE_IGNORE_HTTP_CODES = []
#HTTPCACHE_STORAGE = 'scrapy.extensions.httpcache.FilesystemCacheStorage'

# # 本机数据库地址
# MYSQL_HOST = 'localhost'
# # 数据库用户名:
# MYSQL_USER = 'root'
# #数据库密码
# MYSQL_PASSWORD = 'xjy0430'
# #数据库端口
# MYSQL_PORT = 3306
# #数据库名称
# MYSQL_DBNAME = 'treefrog'
# #数据库编码
# MYSQL_CHARSET = 'utf8'

# 远程数据库地址
MYSQL_HOST = '101.37.75.201'
# 数据库用户名:
MYSQL_USER = 'root'
#数据库密码
MYSQL_PASSWORD = 'rootsecret111'
#数据库端口
MYSQL_PORT = 12345
#数据库名称
MYSQL_DBNAME = 'treefrog'
#数据库编码
MYSQL_CHARSET = 'utf8'