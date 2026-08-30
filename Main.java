import controller.ElectionController;
import model.Election;
import view.ElectionView;

public class Main {
    public static void main(String[] args) {
        Election model = new Election();
        ElectionView view = new ElectionView();
        ElectionController controller = new ElectionController(model, view);
        
        System.out.println("เริ่มเลือกตั้งประธานชมรม");
        controller.start();
    }
}
