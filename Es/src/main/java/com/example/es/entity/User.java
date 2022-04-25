package com.example.es.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author atsjp
 * @date 2022/4/23 17:42
 */
@Data
@Document(indexName = "users")
public class User {

    @Id
    @Field(type = FieldType.Long)
    private Long id;

    @Field(type = FieldType.Text, analyzer = "ik_smart", searchAnalyzer = "ik_smart")
    private String name;

    @Field(type = FieldType.Keyword)
    private String sex;

    @Field(type = FieldType.Integer)
    private Integer age;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String address;

}
