public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");

        Manager manager = new Manager();

        Simple simple1 = new Simple("Title simple 1", "description simple 1", "NEW");
        manager.addSimple(simple1);
        Simple simple2 = new Simple("Title simple 2", "description simple 2", "NEW");
        manager.addSimple(simple2);

        Epic epic1 = new Epic("Title epic 1", "description epic 1", "NEW");
        manager.addEpic(epic1);

        Sub sub11 = new Sub("Title sub 1", "description sub 1", "NEW", epic1.getId());
        manager.addSub(sub11);
        epic1.subIdArray.add(sub11.id);

        Sub sub12 = new Sub("Title sub 12", "description sub 12", "NEW", epic1.getId());
        manager.addSub(sub12);
        epic1.subIdArray.add(sub12.id);

        Epic epic2 = new Epic("Title epic 2", "description epic 2", "NEW");
        manager.addEpic(epic2);

        Sub sub21 = new Sub("Title sub 21", "description sub 21", "NEW", epic2.getId());
        manager.addSub(sub21);
        epic2.subIdArray.add(sub21.id);

        Simple newSimple1 = new Simple("Title newSimple 1", "description newSimple 1", "IN PROGRESS");
        manager.updateSimple(simple1.getId(), newSimple1);

        Sub newSub11 = new Sub("Title newSub 1", "description newSub 11", "DONE", epic1.getId());
        manager.updateSub(sub11.getId(), newSub11);

        Sub newSub12 = new Sub("Title newSub 12", "description newSub 12", "DONE", epic1.getId());
        manager.updateSub(sub12.getId(), newSub12);

        Epic newEpic1 = new Epic("Title newEpic 1", "description newEpic 1", epic1.getStatus());
        manager.updateEpic(epic1.getId(), newEpic1);

        System.out.println(newEpic1);
        System.out.println("Вывод саб айди " + newEpic1.subIdArray);
    }
}
