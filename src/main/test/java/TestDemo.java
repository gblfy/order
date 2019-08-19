import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author gblfy
 * @ClassNme TestDemo
 * @Description TODO
 * @Date 2019/8/19 12:09
 * @version1.0
 */
@Slf4j
public class TestDemo {


    @Test
    public void test() {
        String name = "大家好！my name is gblfy";
        log.info("这是一个测试"+name);
    }

    public static void main(String[] args){
        String name = "大家好！my name is gblfy";
        log.info("这是一个测试"+name);
    }
}
