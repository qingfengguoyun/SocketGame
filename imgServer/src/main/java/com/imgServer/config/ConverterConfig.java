package com.imgServer.config;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.math.BigInteger;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Configuration
public class ConverterConfig implements WebMvcConfigurer {

//    @Override
//    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//        for (HttpMessageConverter<?> converter : converters) {
//            if (converter instanceof MappingJackson2HttpMessageConverter) {
//                ObjectMapper objectMapper = ((MappingJackson2HttpMessageConverter) converter).getObjectMapper();
//                // 忽略值为 null 的属性
//                objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//
//                // 注册自定义的 Long 类型序列化器
//                SimpleModule module = new SimpleModule();
//                module.addSerializer(Long.class, new LongToStringSerializer());
//                objectMapper.registerModule(module);
//            }
//        }
//    }

//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters){
//
//    }

    public static FastJsonConfig config=new FastJsonConfig();

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        List<HttpMessageConverter<?>> converters1 = converters;
        //移除默认的jackson转化器
        Iterator<HttpMessageConverter<?>> iterator = converters.iterator();
        while(iterator.hasNext()){
            HttpMessageConverter<?> converter = iterator.next();
            if(converter instanceof MappingJackson2HttpMessageConverter){
                iterator.remove();
            }
        }
        //设置FastJsonHttpMessageConverter
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter=new FastJsonHttpMessageConverter();
//       将空的集合序列化为 []，使用日期格式进行序列化以及禁用循环引用检测。
        config.setSerializerFeatures(SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteDateUseDateFormat,
                SerializerFeature.DisableCircularReferenceDetect);
        config.setDateFormat("yyyy-MM-dd HH:mm:ss");
        SerializeConfig serializeConfig = SerializeConfig.globalInstance;
        serializeConfig.put(BigInteger.class, ToStringSerializer.instance);
        //为何需要将 Long 转化为String：前端number类型只能接收17位，超出的部分会失真
//        将 Long 类型(包装类）的对象序列化为字符串
        serializeConfig.put(Long.class,ToStringSerializer.instance);
//        将 long 类型（基本类型）的对象序列化为字符串
        serializeConfig.put(Long.TYPE,ToStringSerializer.instance);
        serializeConfig.put(Date.class,new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
        config.setSerializeConfig(serializeConfig);
        fastJsonHttpMessageConverter.setFastJsonConfig(config);

        converters.add(fastJsonHttpMessageConverter);

    }

}
