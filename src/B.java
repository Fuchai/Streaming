
class B {
    int k;
    A[] as;

    public B(int k) {
        this.k = k;
        as = new A[k];
        for (int i = 0; i < k; i++) {
            as[i] = new A();
        }
    }

    void receive(int num) {
        for (int i = 0; i < k; i++) {
            as[i].receive(num);
        }
    }

    double output() {
        double c = 0;
        for (int i = 0; i < k; i++) {
            c += as[i].output();
//            System.out.println(as[i].output());
        }
        return c / k;
    }
}