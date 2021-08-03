package api;

import org.testng.Assert;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import org.testng.annotations.Parameters;


public class AllTests {
    BankBase bankBase = new BankBase();

    @Test(priority = 1)
    public void validateEmptyEndpoint(){
        if (!bankBase.checkEmptyEndpoints()) {
            Assert.assertFalse(bankBase.checkEmptyEndpoints(), "Error with the endpoint");
            bankBase.amountOfClients();
            bankBase.emptyApiEndpoint();
            bankBase.amountOfClients();
        }
        Assert.assertTrue(bankBase.checkEmptyEndpoints(),"The endpoint has data");
    }

    @Test(priority=2)
    @Parameters("amountOfClients")
    public void fakeUsersInitialize(@Optional("10") int users){
        bankBase.initializePojoData(users);
        bankBase.postClients();
        //Assert.assertEquals(bankBase.amountOfClients(), users);
        bankBase.getClients();

    }

    @Test(priority=3)
    public void checkDuplicateEmails(){
         Assert.assertFalse(bankBase.checkDuplicatesEmails(),"Clients Data has duplicates emails.");

    }

    @Test(priority=4)
    public void updatingAccountNumber(){
        Assert.assertEquals(bankBase.updateAccountEndpoint(), 200);
        bankBase.getClients();
    }




}

