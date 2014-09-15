package com.codingserbia;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Reducer;

import com.codingserbia.dto.CustomerCategoryProductBag;
import com.codingserbia.writable.CustomerSessionWritable;
import com.codingserbia.writable.ProductWritable;

public class CustomerRecordsReducer extends Reducer<LongWritable, CustomerSessionWritable, LongWritable, Text> {

    private Map<LongWritable, CustomerCategoryProductBag> categoryMap;

    public CustomerRecordsReducer() {
        super();

        categoryMap = new HashMap<LongWritable, CustomerCategoryProductBag>();
    }

    @Override
    protected void reduce(LongWritable key, Iterable<CustomerSessionWritable> values, Context context) throws IOException, InterruptedException {
        CustomerCategoryProductBag aBag = categoryMap.get(key);
        if (aBag == null) {
            aBag = new CustomerCategoryProductBag();
            aBag.customerCategoryId = key;
        }

        for (CustomerSessionWritable value : values) {
            aBag.increaseNumberOfSessions();
            Writable[] productWritables = value.products.get();

            for (Writable writable : productWritables) {
                ProductWritable product = (ProductWritable) writable;

                if (!aBag.contains(product.id)) {
                    aBag.add(product);
                } else {
                    aBag.processOccurance(product);
                }
            }
        }

        categoryMap.put(key, aBag);
        List<ProductWritable> topProducts = aBag.getTopProductsBought(5);

        System.out.println(aBag.calculateAverageNumberOfViews());

        System.out.println(aBag.calculateAverageNumberOfPurchases());

        System.out.println(aBag.calculateAveragePurchase());

        System.out.println("reducer out");

    }
}
