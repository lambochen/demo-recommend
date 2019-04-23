package com.chenlinghong.mahout;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @Description 基于物品的推荐
 * @Author chenlinghong
 * @Date 2019/4/23 9:57
 * @Version V1.0
 */
public class ItemBasedRecommender {

    @Test
    public void itemBasedRecommender() throws IOException, TasteException {
        File modelFile = new File("F:\\code\\demo-recommend\\mahout\\data\\cf\\intro.csv");

        // 数据模型
        DataModel dataModel = new FileDataModel(modelFile);

        // Build the same recommender for testing that we did last time:
        RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
            @Override
            public Recommender buildRecommender(DataModel model) throws TasteException {
                ItemSimilarity similarity = new PearsonCorrelationSimilarity(model);
                return new GenericItemBasedRecommender(model, similarity);
            }
        };

        // 获取推荐结果,给用户1推荐4条数据
        List<RecommendedItem> itemList = recommenderBuilder.buildRecommender(dataModel).recommend(1,4);

        /**
         * 输出推荐结果
         */
        for (RecommendedItem recommendation : itemList) {
            System.out.println(recommendation);
        }

        /**
         *
         */


    }
}
