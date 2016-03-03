package MyPack;

import javax.xml.crypto.Data;
import java.io.*;
import java.util.Date;

/**
 * Created by taras on 27.02.2016.
 */
public class Payments implements Serializable{
    int customerNumber;
    Date paymentDate;
    float amount;

    public Payments(Date paymentDate, int customerNumber, float amount) {
        this.paymentDate = paymentDate;
        this.customerNumber = customerNumber;
        this.amount = amount;
    }
}
