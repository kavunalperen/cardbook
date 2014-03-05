package com.abdullah.cardbook.adapters;

import com.abdullah.cardbook.models.Company;

/**
 * Created by abdullah on 10/25/13.
 */
public interface KampanyaListener {

    public void openCampaign(int companyId);
    public void openCampaignDetail(String campaignId);
}
