package api;

import com.github.javafaker.Faker;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class BankBase {
    private final String url = "https://60f58a6818254c00176dff1b.mockapi.io/api/academy/users/";
    private PojoData bankClients;
    private PojoData[] allClientsData;
    private final ArrayList<PojoData> newClient;

    Faker fakerData = new Faker();

    public BankBase() {
        bankClients = new PojoData();
        newClient = new ArrayList<>();
    }

    //Method to get all clients
    public void getClients() {
        Response response;
        int status = 0;
        do {
            response = given().contentType("application/json").when().get(url);
            status = response.statusCode();
        } while (status != 200);
        allClientsData = response.as(PojoData[].class);
    }

    //Check if the endpoint is empty
    public boolean checkEmptyEndpoints() {
        getClients();
        return Arrays.stream(allClientsData).count() == 0;
    }

    public void emptyApiEndpoint() {
        PojoData[] clients = allClientsData;
        int status = 0;
        Response response;
        do {
            for (PojoData bankClients : clients) {
                String cliendId = bankClients.getId();
                response = given().contentType("applocation/json").when().delete((url + "/" + (cliendId)));
            }
        } while (status != 200);
    }

    public void newFakerDataClients(){
        String[] TransactionType = {"payment", "withdrawal", "wire-transfer"};
        bankClients = new PojoData();
        bankClients.setName(fakerData.name().firstName());
        bankClients.setName(fakerData.name().lastName());
        bankClients.setAccountNumber(fakerData.finance().iban());
        bankClients.setAmount(String.valueOf(fakerData.number().randomDouble(2,1,500)));
        bankClients.setTransactionType(TransactionType[new Random().nextInt(3)]);
        bankClients.setEmail(fakerData.internet().emailAddress());
        //bankClients.setActive(fakerData.boolean().boolean());
        bankClients.setActive(fakerData.bool().bool());
        bankClients.setCountry(fakerData.address().country());
        bankClients.setPhone(fakerData.phoneNumber().phoneNumber());
    }

    public void initializePojoData(int numberOfClients){
        for(int i = 0; i < numberOfClients; i++) {
            newFakerDataClients();
            newClient.add(bankClients);
        }
    }

    public boolean validEmail(String validEmail) {
        PojoData[] users;
        Response response;
        int status = 0;
        do {
            response = given().queryParam("email",validEmail).when().get(url);
            status = response.getStatusCode();
        } while (status !=200);
        users = response.as(PojoData[].class);
        return users.length > 0;
    }

    public void postClients(){
        int status = 0;
        if(!validEmail(bankClients.getEmail())) {
            Response response;
            do {
                response = given().contentType("application/json").body(bankClients).when().post(url);
                status = response.statusCode();
            } while (status != 201);
        }
    }

    public void postPojoClientsEndpoint(){
        for(PojoData newUser: newClient){
            bankClients = newUser;
            postClients();
        }
    }

    public boolean checkDuplicatesEmails() {
        getClients();
        PojoData[] users = allClientsData;
        ArrayList<String> newEmail = new ArrayList<String>();
        for (PojoData bankUsers : users) {
            newEmail.add(bankUsers.getEmail());
        }
        for (int i = 0; i < newEmail.size(); i++){
            for (int j = 1; j < newEmail.size(); j++){
                if(i!=j && newEmail.get(i) == newEmail.get(j)){
                    return true;
                }
            }
            System.out.println(newEmail.get(i));
        }
        return false;
    }

    public long amountOfClients(){
        getClients();
        return allClientsData.length;
    }

    public void getClientsandPrintthem() {
        Response response;
        int status = 0;
        do {
            response = given().contentType("application/json").when().get(url);
            status = response.statusCode();
        } while (status != 200);
        response.prettyPrint();
    }

    public int updateAccountEndpoint(){
        getClients();
        PojoData[] users = allClientsData;
        HashMap<String, String> accountNumber = new HashMap<>();
        accountNumber.put("accountNumber", fakerData.finance().iban());
        long size = amountOfClients();
        long newId = new Random().nextInt((int) size);
        int status = 0;
        Response response;
        for (PojoData bankUsers : users) {
            String id = bankUsers.getId();
            String numberAccount = bankUsers.getAccountNumber();
            if(Long.valueOf(id) == newId) {
                 do {
                    response = given().contentType("application/json").body(accountNumber).when().put(url+"id");
                    status = response.getStatusCode();
                 } while (status != 200);
            }
        }
        getClients();
        return status;
    }







}
