package com.socialNetwork.socialNetwork.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.HashSet;
import java.util.Set;

import static com.socialNetwork.socialNetwork.utils.Constant.COLLECTION.USER_COLLECTION;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(USER_COLLECTION)
public class User  extends BaseEntity{

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String EMAIL = "email";
    public static final String FIRSTNAME = "firstname";
    public static final String LASTNAME = "lastname";
    public static final String GENDER = "gender";
    public static final String ADDRESS = "address";
    public static final String AVT = "avt";
    public static final String DAYOFBIRTH = "DOB";
    public static final String LAST_LOGIN_TIME = "last_login_time";
    public static final String AUTHORITIES = "authorities";
    public static final String LOGIN_TIME = "login_time";
    public static final String PLAYER_ID = "player_id";


    @Field(USERNAME)
    private String username;

    @Field(PASSWORD)
    private String password;

    @Field(EMAIL)
    private String email;

    @Field(FIRSTNAME)
    private String firstName;

    @Field(LASTNAME)
    private String lastName;

    @Field(GENDER)
    private Boolean gender; //1 is male 0 is female

    @Field(ADDRESS)
    private String address;

    @Field(AVT)
    private Byte[] avt;

    @Field(DAYOFBIRTH)
    private String DOB;

    @Field(LAST_LOGIN_TIME)
    private String lastLoginTime;

    @Field(AUTHORITIES)
    private Set<String> authorities = new HashSet<>();

    @Field(LOGIN_TIME)
    private String loginTime;

    @Field(PLAYER_ID)
    private String playerId;

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }
}
