package com.chenlinghong.mahout;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.CachingRecommender;
import org.apache.mahout.cf.taste.impl.recommender.slopeone.SlopeOneRecommender;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @Description SlopeOne 基于评分的方式推荐
 * @Author chenlinghong
 * @Date 2019/4/23 14:20
 * @Version V1.0
 */
public class MySlopeOneRecommender {

    /**
     * https://www.php3.cn/a/178.html
     *
     * 假设系统对于物品 A，物品 B 和物品 C 的平均评分分别是 3，4 和 4。基于 Slope One 的方法会得到以下规律：
     *
     * •用户对物品 B 的评分 = 用户对物品 A 的评分 + 1
     * •用户对物品 B 的评分 = 用户对物品 C 的评分
     *
     * 基于以上的规律，我们可以对用户 A 和用户 B 的打分进行预测：
     *
     * •对用户 A，他给物品 A 打分 4，那么我们可以推测他对物品 B 的评分是 5，对物品 C 的打分也是 5。
     * •对用户 B，他给物品 A 打分 2，给物品 C 打分 4，根据第一条规律，我们可以推断他对物品 B 的评分是 3；
     *          而根据第二条规律，推断出评分是 4。当出现冲突时，我们可以对各种规则得到的推断进行就平均，所以给出的推断是 3.5。
     *
     * 这就是 Slope One 推荐的基本原理，它将用户的评分之间的关系看作简单的线性关系：
     * Y = mX + b;
     */

    @Test
    public void slopeOneRecommender() throws IOException, TasteException {
        File modelFile = new File("F:\\code\\demo-recommend\\mahout\\data\\cf\\intro.csv");
        DataModel dataModel = new FileDataModel(modelFile);

        // 采用SlopeOneCommender
        Recommender recommender = new CachingRecommender(new SlopeOneRecommender(dataModel));

        List<RecommendedItem> itemList = recommender.recommend(1,4);

        for (RecommendedItem item : itemList){
            System.out.println(item);
        }

    }

}
