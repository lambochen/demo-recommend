#!/usr/bin/env python
# -*- coding: utf-8 -*-
# author：chenlinghong
# time: 2019/2/27
import os


def get_user_click(rating_file):
    """
    获取user点击列表
    :param rating_file:
    :return: dict, key:userid, value:[itemid1, itemid2]
    """
    if not os.path.exists(rating_file):
        return {}
    fp = open(rating_file)
    # 声明计数器，用于跳过文件第一行提示文字
    num = 0
    # 用户点击列表
    user_click = {}
    for line in fp:
        if num == 0:
            num += 1
            continue
        item = line.strip().split('::')
        if len(item) < 4:
            continue
        [userid, itemid, rating, timestamp] = item
        # 设定阈值，小于则抛弃，大于则认为用户喜欢该item
        if float(rating) < 3.0:
            continue
        if userid not in user_click:
            user_click[userid] = []
        user_click[userid].append(itemid)
    fp.close()
    return user_click


def get_item_info(item_file):
    """
    获取item信息 [title, genres]
    :param item_file:
    :return:    a dict , key:itemid, value:[title,genres]
    """
    if not os.path.exists(item_file):
        return {}
    # 设立监听器，用于跳过文件第一行说明文字
    num = 0
    # 结果
    item_info = {}
    fp = open(item_file)
    for line in fp:
        if num == 0:
            num += 1
            continue
        item = line.strip.split('::')
        if len(item) < 3:
            continue
        [itemid, title, genres] = item
        if itemid not in item_info:
            item_info[itemid] = [title, genres]
    fp.close()
    return item_info


if __name__ == "__name__":
    user_click = get_user_click("../data/ratings.txt")
    print len(user_click)
    print user_click["1"]
