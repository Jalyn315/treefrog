# -*- coding: utf-8 -*-
import scrapy
from scrapy.linkextractors import LinkExtractor
from scrapy.spiders import CrawlSpider, Rule
from ..items import MyspiderItem


class Cc518Spider(CrawlSpider):
    name = 'cc518'
    allowed_domains = ['www.cc518.com']
    start_urls = ['http://www.cc518.com/']
    # spider中设置管道
    custom_settings = {
        'ITEM_PIPELINES': {
            'mySpider.items.MyspiderItem': 1,
            # 'mySpider.pipelines.JsonEncodingPipeline': 101,
            'mySpider.pipelines.MySQLPipeline': 100,
            # 'mySpider.pipelines.EncodingTxtPipeline': 102,  # 从数据库写入文本中
        }
    }
    rules = (
        # 匹配大分类url地址和小分类的url
        Rule(LinkExtractor(restrict_xpaths=("//ul[@class='nav']/li",)), follow=True),  # scrapy 爬取URL地址时会自动去重，爬过的就不会再爬了
        # 匹配文章的URL地址，并且通过callback指定的函数，跳转进入该URL下的网页中，请求数据
        Rule(LinkExtractor(restrict_xpaths=("//div[@class='list-item']/ul/li",)), callback="parse_article_detail", follow=True),
        # 列表页翻页
        Rule(LinkExtractor(restrict_xpaths=("//div[@class='iCMS_pagination']/a",)), follow=True),
    )

    def parse_article_detail(self, response):
        item = MyspiderItem()
        item["big_category"] = ''.join(response.xpath("//ul[@class='breadcrumb']/li[2]/a/text()").extract_first())
        item["bc_url"] = ''.join(response.xpath("//ul[@class='breadcrumb']/li[2]/a/@href").extract_first())
        item["small_category"] = ''.join(response.xpath("//ul[@class='breadcrumb']/li[3]/a/text()").extract_first())
        item["sc_url"] = ''.join(response.xpath("//ul[@class='breadcrumb']/li[3]/a/@href").extract_first())
        item["article_title"] = ''.join(response.xpath("//ul[@class='breadcrumb']/li[4]/a/text()").extract_first())
        item["article_url"] = ''.join(response.xpath("//ul[@class='breadcrumb']/li[4]/a/@href").extract_first())
        item["article_tags"] = ''.join(response.xpath("//div[@class='article-tags']/a/text()").extract_first())
        p_content = response.xpath("//div[@class='article-body']/p/text()|//div[@class='article-body']//h1/text()").extract()
        span_content = response.xpath("//div[@class='article-body']//span/text()").extract()
        img_content = '\n'.join(response.xpath("//div[@class='article-body']//img/@src").extract())

        # 数据清洗，对数据进行简单的处理
        if len(p_content) > 1:
            p_content = '\n'.join(response.xpath("//div[@class='article-body']/p/text()|//div[@class='article-body']//h1/text()").extract())
        else:
            p_content = ''.join(response.xpath("//div[@class='article-body']/p/text()|//div[@class='article-body']//h1/text()").extract())
        if len(span_content) > 1:
            span_content = '\n'.join(response.xpath("//div[@class='article-body']/p/text()|//div[@class='article-body']//h1/text()").extract())
        else:
            span_content = ''.join(response.xpath("//div[@class='article-body']/p/text()|//div[@class='article-body']//h1/text()").extract())

        if len(p_content) > 0 and len(span_content) > 0:
            item["article_content"] = p_content + "\n" + span_content
            if len(img_content) > 0:
                item["article_content"] = item["article_content"] + "\n" + img_content
        elif len(p_content) > 0:
            item["article_content"] = p_content
            if len(img_content) > 0:
                item["article_content"] = item["article_content"] + "\n" + img_content
        elif len(span_content) > 0:
            item["article_content"] = span_content
            if len(img_content) > 0:
                item["article_content"] = item["article_content"] + "\n" + img_content
        elif len(img_content) > 0:
            item["article_content"] = img_content
        else:
            item["article_content"] = None

        # item["tag"] = 0
        # item["user_id"] = 2
        # item["category_id"] = 0
        # item["is_downloadable"] = 0
        # item["is_uploadable"] = 0
        # item["is_visible"] = 0
        # item["is_deletable"] = 0
        # item["is_updatable"] = 0
        # 提交数据到 pipeline处理
        yield item
