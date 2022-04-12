import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.client.HttpStatusCodeException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {

        while(true) {
            Scanner in = new Scanner(System.in);
            System.out.print("Input a request name: ");
            String command = in.nextLine();

            try {
                switch (command) {
                    case "get all diagnosis" -> Request.getAllDiagnosis();
                    case "get diagnosis" -> Request.getDiagnosis();
                    case "get all wards" -> Request.getAllWards();
                    case "get ward" -> Request.getWards();
                    case "get all peoples" -> Request.getAllPeoples();
                    case "get people" -> Request.getPeople();
                    case "get ward by people" -> Request.getWardByPeople();
                    case "get diagnosis by people" -> Request.getDiagnosisByPeople();
                    case "get people in ward" -> Request.getAllPeopleInWard();
                    case "add diagnosis" -> Request.addDiagnosis();
                    case "add ward" -> Request.addWard();
                    case "add people" -> Request.addPeople();
                    case "sign in" -> {
                        try {
                            Request.authorization();
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                    }
                    case "delete people by id" -> Request.deletePeople();
                    case "delete diagnosis by id" -> Request.deleteDiagnosis();
                    case "delete ward by id" -> Request.deleteWard();
                    case "delete  all peoples" -> Request.deleteAllPeoples();
                    case "delete all diagnoses" -> Request.deleteAllDiagnoses();
                    case "delete all wards" -> Request.deleteAllWards();
                    case "update people" -> Request.updatePeople();
                    case "update diagnosis" -> Request.updateDiagnosis();
                    case "update ward" -> Request.updateWard();

                    default -> System.out.println("Команда не найдена");

                }
            }catch (InputMismatchException e){
                System.out.println("Ошибка формата вовода данных ");
            }catch (HttpStatusCodeException e) {
                System.out.println("ERROR "+ e.getStatusCode());
            }
        }
    }
}
