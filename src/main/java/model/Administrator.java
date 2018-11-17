package model;

import org.json.JSONObject;

public class Administrator extends User {
    public Administrator() {
    }


    public static class Creator implements JsonCreator<Administrator> {
        public Creator() {
        }

        @Override
        public Administrator fromJson(JSONObject jsonObject) {
            Administrator administrator = new Administrator();
            administrator.id = jsonObject.getInt(("id"));
            administrator.name = jsonObject.getString("name");
            administrator.username = jsonObject.getString("username");
            return administrator;
        }
    }
}
