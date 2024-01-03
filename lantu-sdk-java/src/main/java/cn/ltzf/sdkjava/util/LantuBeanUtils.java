package cn.ltzf.sdkjava.util;

import cn.ltzf.sdkjava.common.annotation.Required;
import cn.ltzf.sdkjava.common.error.LantuPayErrorException;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 蓝兔支付的Bean操作的相关Utils类
 *
 * @author Wuchubuzai
 * @date 2024-01-02 17:20:25
 */
public class LantuBeanUtils {
    
    private static Logger logger = LoggerFactory.getLogger(LantuBeanUtils.class);
    
    /**
     * 检查bean里标记为@Required的field是否为空，为空则抛异常
     *
     * @param bean 要检查的bean对象
     */
    public static void checkRequiredFields(Object bean) throws LantuPayErrorException {
        List<String> requiredFields = Lists.newArrayList();
        
        List<Field> fields = new ArrayList<>(Arrays.asList(bean.getClass().getDeclaredFields()));
        fields.addAll(Arrays.asList(bean.getClass().getSuperclass().getDeclaredFields()));
        for (Field field : fields) {
            try {
                boolean isAccessible = field.isAccessible();
                field.setAccessible(true);
                if (field.isAnnotationPresent(Required.class)) {
                    // 两种情况，一种是值为null，
                    // 另外一种情况是类型为字符串，但是字符串内容为空的，都认为是没有提供值
                    boolean isRequiredMissing = field.get(bean) == null
                            || (field.get(bean) instanceof String
                            && StringUtils.isBlank(field.get(bean).toString())
                    );
                    if (isRequiredMissing) {
                        requiredFields.add(field.getName());
                    }
                }
                field.setAccessible(isAccessible);
            } catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
                logger.error(e.getMessage(), e);
            }
        }
        
        if (!requiredFields.isEmpty()) {
            String msg = String.format("必填字段【%s】必须提供值！", requiredFields);
            logger.debug(msg);
            throw new LantuPayErrorException(msg);
        }
    }
    
}
