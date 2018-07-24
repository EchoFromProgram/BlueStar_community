package util;

import org.junit.Test;
import com.bluestar.common.utils.ListUtil;

/**
 * ListUtil 工具类测试类
 *
 * @author Fish
 * created by 2018-06-02 11:03
 */
public class ListUtilTest {

    @Test
    public void testStrings2Integers()
    {
        System.out.println(ListUtil.strings2integers(new String[] {
                "142", "34", "3563", "5352   "
        }));
    }
}
