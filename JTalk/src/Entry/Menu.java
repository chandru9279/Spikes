package Entry;

import Models.Address;
import Speak.GetData;
import Speak.SaveData;
import Talk.PersonXChange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Menu {

    HashMap<Integer, InputHandler> menu;
    BufferedReader in;

    public Menu(final GetData getData, final SaveData saveData, final PersonXChange personXChange) {
        in = new BufferedReader(new InputStreamReader(System.in));
        menu = new HashMap<Integer, InputHandler>(5);
        menu.put(1, new InputHandler() {
            @Override
            public boolean doOnSelect() throws IOException {
                getData.getAllSimple();
                return true;
            }
        });
        menu.put(2, new InputHandler() {
            @Override
            public boolean doOnSelect() throws IOException {
                getData.getAllComplex();
                return true;
            }
        });
        menu.put(3, new InputHandler() {
            @Override
            public boolean doOnSelect() throws IOException {
                getData.getLast();
                return true;
            }
        });
        menu.put(4, new InputHandler() {
            @Override
            public boolean doOnSelect() throws IOException {
                out("Message :");
                saveData.sendMessage(in.readLine());
                return true;
            }
        });
        menu.put(5, new InputHandler() {
            @Override
            public boolean doOnSelect() throws IOException {
                out("Messages SPACE Separated Example : \"Hello World Server\"");
                String[] messages = in.readLine().split(" ");
                out("Metadata - pattern \"key:value\" SPACE Separated Example : \"ignore:false status:urgent\"");
                String[] metadata = in.readLine().split(" ");
                HashMap<String, String> meta = new HashMap<String, String>();
                for (int i = 0; i < metadata.length; i++)
                    if (saveData.isNotEmpty(metadata[i]) && metadata[i].contains(":")) {
                        String[] keyValue = metadata[i].split(":");
                        meta.put(keyValue[0], keyValue[1]);
                    }
                saveData.sendComplexMessage(meta, messages);
                return true;
            }
        });
        menu.put(6, new InputHandler() {
            @Override
            public boolean doOnSelect() throws IOException {
                personXChange.getLast();
                return true;
            }
        });
        menu.put(7, new InputHandler() {
            @Override
            public boolean doOnSelect() throws IOException {
                personXChange.getPeople();
                return true;
            }
        });
        menu.put(8, new InputHandler() {
            @Override
            public boolean doOnSelect() throws IOException {
                out("Enter Birthday  like. 1980-08-25 :  ");
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date birthDate;
                try {
                    birthDate = formatter.parse(in.readLine());
                } catch (ParseException e) {
                    e.printStackTrace();
                    return true;
                }
                out("Using birthday as this : " + birthDate);
                out("Enter addresses Semi-Colon ';' separated, Enter in order of priority - primary first; secondary next; other third; and so on..");
                String[] addresses = in.readLine().split(";");
                ArrayList<Address> addressList = new ArrayList<Address>(addresses.length);
                for (int i = 0; i < addresses.length; i++)
                    if (saveData.isNotEmpty(addresses[i]))
                        addressList.add(new Address(i + 1, addresses[i]));
                personXChange.sendPerson(birthDate, addressList);
                return true;
            }
        });
        menu.put(9, new InputHandler() {
            @Override
            public boolean doOnSelect() throws IOException {
                return false;
            }
        });
    }

    public boolean show() throws IOException {
        out("\n\n       -=Menu=- ");
        out("1. Get all simple messages");
        out("2. Get all complex messages");
        out("3. Get last simple message");
        out("4. Save simple message");
        out("5. Save complex message");
        out("6. Get Last Person");
        out("7. Get People");
        out("8. Save Person");
        out("9. Exit");
        int option = Integer.parseInt(in.readLine());
        return menu.get(option).doOnSelect();
    }

    private void out(String prompt) {
        System.out.println(prompt);
    }

    public interface InputHandler {
        boolean doOnSelect() throws IOException;
    }
}
