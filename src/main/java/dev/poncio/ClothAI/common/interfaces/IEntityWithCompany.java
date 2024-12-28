package dev.poncio.ClothAI.common.interfaces;

import dev.poncio.ClothAI.company.CompanyEntity;

public interface IEntityWithCompany {

    Long getId();

    CompanyEntity getCompany();

}
