

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class Test1 {
     Adding testAdding = new Adding();
    @Test
    public void AddTwoNumbers(){
        int y= 2;
        int d = 2;

        int result = testAdding.AddTwoNumbers1(y,d);

    }

    class Adding{

        int AddTwoNumbers1(int a, int b){
            return a + b;
        }


    }
}
