package sample;

import sample.Enums.Fields;

import java.util.HashMap;

public class AUserFactory {

    public User generate(HashMap<String, String> details)
    {
            User u = new User(details.get(Fields.userID.name()),
                    details.get(Fields.rank.name()),
                    details.get(Fields.warning.name()),
                    details.get(Fields.status.name()),
                    details.get(Fields.Username.name()),
                    details.get(Fields.password.name()));
            return u;
    }
}
