package com.chenlinghong.mahout;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.IRStatistics;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.eval.RecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.GenericRecommenderIRStatsEvaluator;
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
        File modelFile = new File("F:\\code\\demo-recommend\\mahout\\data\\cf\\ml-ratings.dat");

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
        List<RecommendedItem> itemList = recommenderBuilder.buildRecommender(dataModel).recommend(1, 4);

        /**
         * 输出推荐结果
         */
        for (RecommendedItem recommendation : itemList) {
            System.out.println(recommendation);
        }

        /**
         * 计算评分
         */
        RecommenderEvaluator evaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();

        // 使用70%的数据训练，另外30%的数据测试
        double score = evaluator.evaluate(recommenderBuilder, null, dataModel, 0.7, 1.0);
        System.out.println("evaluator#evalute: score = " + score);

        // 计算查全率和查准率
        RecommenderIRStatsEvaluator statsEvaluator = new GenericRecommenderIRStatsEvaluator();

        // Evaluate precision and recall "at 2":
        IRStatistics stats = statsEvaluator.evaluate(recommenderBuilder,
                null, dataModel, null, 4,
                GenericRecommenderIRStatsEvaluator.CHOOSE_THRESHOLD,
                1.0);
        System.out.println("precision = " + stats.getPrecision());
        System.out.println("recall = " + stats.getRecall());

    }
}
