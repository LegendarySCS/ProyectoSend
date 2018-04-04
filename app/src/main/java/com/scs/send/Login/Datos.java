package com.scs.send.Login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Ac-Ad on 27/3/2018.
 */

public class Datos extends AppCompatActivity {



    public String Cell(Bundle bundle) {
        String cell = bundle.getString("Cell");
        return cell;
    }
    public String nombre(Bundle bundle) {
        String nombre = bundle.getString("NombreUser");
        return nombre;
    }
    public String email(Bundle bundle) {
        String emaid = bundle.getString("EmailUser");
        return emaid;

    }
    public String id(Bundle bundle) {

        String id = bundle.getString("IdUnico");
        return id;

    }
}
