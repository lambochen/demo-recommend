package com.chenlinghong.mahout;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.CachingRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @Description 基于用户推荐
 * @Author chenlinghong
 * @Date 2019/4/21 12:14
 * @Version V1.0
 */
public class UserBasedRecommender {

    @Test
    public void userBasedRecommender() throws IOException, TasteException {
        File file = new File("F:\\code\\demo-recommend\\mahout\\data\\cf\\intro.csv");
        DataModel dataModel = new FileDataModel(file);

        // 用户相似度计算，采用皮尔逊相关系数计算
        UserSimilarity userSimilarity = new PearsonCorrelationSimilarity(dataModel);

        // 选择邻居用户，使用NearestNUserNeighborhood实现UserNeighborhood接口，选择邻近的4个用户
        UserNeighborhood userNeighborhood = new NearestNUserNeighborhood(4,userSimilarity, dataModel);

        /**
         * 构造推荐器
         */
        Recommender genericUserBasedRecommender = new GenericUserBasedRecommender(dataModel, userNeighborhood, userSimilarity);

        // CachingRecommender来包装GenericUserBasedRecommender对象，将推荐结果缓存起来
        Recommender recommender = new CachingRecommender(genericUserBasedRecommender);

        // 给用户 1 推荐 4 个物品
        List<RecommendedItem> itemList = recommender.recommend(1,4);

        for (RecommendedItem item : itemList){
            System.out.println(item);
        }
    }

}
