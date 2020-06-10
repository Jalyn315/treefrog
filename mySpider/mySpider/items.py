# -*- coding: utf-8 -*-

# Define here the models for your scraped items
#
# See documentation in:
# https://doc.scrapy.org/en/latest/topics/items.html

import scrapy


class MyspiderItem(scrapy.Item):
    # define the fields for your item here like:
    # name = scrapy.Field()
    big_category = scrapy.Field()
    bc_url = scrapy.Field()
    small_category = scrapy.Field()
    sc_url = scrapy.Field()
    article_title = scrapy.Field()
    article_url = scrapy.Field()
    article_tags = scrapy.Field()
    article_content = scrapy.Field()

    id = scrapy.Field()
    name = scrapy.Field()
    suffix = scrapy.Field()
    local_url = scrapy.Field()
    visit_url = scrapy.Field()
    size = scrapy.Field()
    create_time = scrapy.Field()
    description = scrapy.Field()
    check_times = scrapy.Field()
    download_count = scrapy.Field()
    tag = scrapy.Field()
    user_id = scrapy.Field()
    category_id = scrapy.Field()
    is_downloadable = scrapy.Field()
    is_uploadable = scrapy.Field()
    is_visible = scrapy.Field()
    is_deletable = scrapy.Field()
    is_updatable = scrapy.Field()
    last_modify_time = scrapy.Field()
