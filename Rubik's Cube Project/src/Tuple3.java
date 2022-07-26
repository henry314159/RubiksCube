
public class Tuple3<A, B, C> {

    private A first;
    private B second;
    private C third;
  
    public Tuple3(A first, B second, C third){
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public A get0() {
    	return first;
    }
    
    public B get1() {
    	return second;
    }
    
    public C get2() {
    	return third;
    }
}
