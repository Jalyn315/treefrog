#!/usr/bin/python3
# -*- coding: utf-8 -*-

"""
@author: JY
@project: Treefrog
@file: file_to_zip.py
@function: 将文件进行压缩处理
@time: 2020-5-17 14:42
"""
import os
import time
import zipfile
import pathlib

def get_zip_file(input_path, result):
    """
    对目录进行深度优先遍历
    :param input_path:
    :param result:
    :return:
    """
    files = os.listdir(input_path)
    for file in files:
        if os.path.isdir(input_path + '\\' + file):
            get_zip_file(input_path + '\\' + file, result)
        else:
            result.append(input_path + '\\'+ file)


def zip_file_path(input_path, output_path, output_name):
    """
    压缩文件
    :param input_path: 压缩的文件夹路径
    :param output_path: 解压（输出）的路径
    :param output_name: 压缩包名称
    :return:
    """
    zip_path = output_path + "\\" + output_name
    z = pathlib.Path(zip_path)
    f = zipfile.ZipFile(zip_path, 'w', zipfile.ZIP_DEFLATED)
    filelists = []
    get_zip_file(input_path, filelists)
    for file in filelists:
        f.write(file)
    # 调用了close方法才会保证完成压缩
    f.close()
    time.sleep(0.5)
    # 文件大小默认以Bytes计， 转换为Mb
    zip_size = z.stat().st_size
    print('Zip_file size = %.2f Mb(%.2f Kb)' % (zip_size / 1024 / 1024, zip_size / 1024))
    return zip_path, zip_size


if __name__ == '__main__':
    zip_file_path(".\\初中", '.\\', '初中.zip')