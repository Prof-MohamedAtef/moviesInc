
package mo.ed.movies.inc.responses.crewListResponse;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class Cast {

    @SerializedName("cast_id")
    private Long mCastId;
    @SerializedName("character")
    private String mCharacter;
    @SerializedName("credit_id")
    private String mCreditId;
    @SerializedName("gender")
    private Long mGender;
    @SerializedName("id")
    private Long mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("order")
    private Long mOrder;
    @SerializedName("profile_path")
    private Object mProfilePath;

    public Long getCastId() {
        return mCastId;
    }

    public void setCastId(Long castId) {
        mCastId = castId;
    }

    public String getCharacter() {
        return mCharacter;
    }

    public void setCharacter(String character) {
        mCharacter = character;
    }

    public String getCreditId() {
        return mCreditId;
    }

    public void setCreditId(String creditId) {
        mCreditId = creditId;
    }

    public Long getGender() {
        return mGender;
    }

    public void setGender(Long gender) {
        mGender = gender;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Long getOrder() {
        return mOrder;
    }

    public void setOrder(Long order) {
        mOrder = order;
    }

    public Object getProfilePath() {
        return mProfilePath;
    }

    public void setProfilePath(Object profilePath) {
        mProfilePath = profilePath;
    }

}
