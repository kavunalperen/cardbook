package com.abdullah.cardbook.models;

import com.abdullah.cardbook.common.AppConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by abdullah on 10/26/13.
 */
public class Campaign {

    /* Gelen Bilgi
    "CampaignId":6,
    "CompanyId":1,
    "UserId":0,
    "CampaignName":"Kampanya 6",
    "CampaignDescription":"Kampanya Tanımı 6",
    "CampaignStartDate":"\/Date(1336165200000)\/",
    "CampaignEndDate":"\/Date(1399237200000)\/",
    "CampaignBanner":"http://test.mycardbook.gen.tr/DynamicImages/CampaignBanner/6.jpg",
    "CampaignIcon":"http://test.mycardbook.gen.tr/DynamicImages/CampaignIcon/6.jpg",
    "Status":1,
    "XUser":1,
    "UUser":1,
    "XDate":"\/Date(1374400358637)\/",
    "UDate":"\/Date(1374400358637)\/",
    "CampaignDetailList":null,
    "CampaignBannerFile":null,
    "CampaignIconFile":null
    */


    private static String ID="CampaignId";
    private static String COMPANY_ID="CompanyId";
    private static String NAME="CampaignName";
    private static String DESCRIPTION="CampaignDescription";
    private static String START_DATE="CampaignStartDate";
    private static String END_DATE="CampaignEndDate";
    private static String BANNER_URL="CampaignBanner";
    private static String ICON_URL="CampaignIcon";
    private static String DETAIL_LIST="CampaignDetailList";


    private String id;
    private String companyId;
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private String bannerUrl;
    private String iconUrl;
    private ArrayList<String> detailList;


    public Campaign(){

    }

    public Campaign(JSONObject object){

        this.id=object.optString(ID);
        this.companyId=object.optString(COMPANY_ID);
        this.name=object.optString(NAME);
        this.description=object.optString(DESCRIPTION);
        this.startDate= AppConstants.parseMsTimestampToDate(object.optString(START_DATE));
        this.endDate= AppConstants.parseMsTimestampToDate(object.optString(END_DATE));
        this.bannerUrl=object.optString(BANNER_URL);
        this.iconUrl=object.optString(ICON_URL);

        JSONArray list=object.optJSONArray(DETAIL_LIST);
        this.detailList=new ArrayList<String>();

        if(list!=null){
            for(int i=0;i<list.length();i++){

                try {
                    detailList.add(list.get(0).toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Campaign(String id, String companyId, String name, Date startDate, Date endDate){
        this.id=id;
        this.companyId=companyId;
        this.name=name;
        this.startDate=startDate;
        this.endDate=endDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDate() {
        SimpleDateFormat format=new SimpleDateFormat("dd MMMM");
        String date=format.format(this.startDate);
        return date;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        SimpleDateFormat format=new SimpleDateFormat("dd MMMM");
        String date=format.format(this.endDate);
        return date;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public ArrayList<String> getDetailList() {
        return detailList;
    }

    public void addDetail(String detail) {
        if(this.detailList==null)
            detailList=new ArrayList<String>();
        else
            this.detailList.add(detail);
    }

    public void setDetailList(ArrayList<String> detailList) {
        this.detailList = detailList;
    }
}
