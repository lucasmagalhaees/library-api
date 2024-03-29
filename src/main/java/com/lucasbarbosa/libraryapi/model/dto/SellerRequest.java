package com.lucasbarbosa.libraryapi.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.lucasbarbosa.libraryapi.driver.validation.SellerAssurance;
import com.lucasbarbosa.libraryapi.model.entity.Seller;
import com.lucasbarbosa.libraryapi.model.enums.LicenseTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;
import java.util.function.Function;

import static com.lucasbarbosa.libraryapi.driver.utils.LibraryUtils.handleCustomerCnpj;
import static com.lucasbarbosa.libraryapi.driver.utils.LibraryUtils.handleCustomerCpf;

/** @author Lucas Barbosa on 10/07/2021 */
@Getter
@Builder
@SellerAssurance
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(
    value = "SellerRequestDTO",
    description = "Data transfer object for sellers registration and token acquirence")
public class SellerRequest {

  @ApiModelProperty(value = "Seller CPF", example = "021.423.543-65")
  private String cpf;

  @ApiModelProperty(value = "Seller company CNPJ", example = "72.871.272/0001-78")
  private String cnpj;

  @ApiModelProperty(value = "Seller company name", example = "Cyberpunk Inc")
  private String companyName;

  @ApiModelProperty(value = "Seller full name", example = "Tim Peterson Morales")
  private String personName;

  @ApiModelProperty(
      value = "Seller license type",
      example = "INDIVIDUAL",
      allowableValues = "INDIVIDUAL, COMPANY")
  private String licenseType;

  public static Seller toDomain(SellerRequest sellerRequest) {
    return assemble.apply(sellerRequest);
  }

  private static String generateToken() {
    return UUID.randomUUID().toString().substring(0, 8);
  }

  public static String retriveSellerDescription(SellerRequest sellerRequestDTO) {
    switch (retriveLicenseType(sellerRequestDTO)) {
      case COMPANY:
        return sellerRequestDTO.getCompanyName();
      case INDIVIDUAL:
        return sellerRequestDTO.getPersonName();
      default:
        return StringUtils.EMPTY;
    }
  }

  public static String retriveDocumentNumber(SellerRequest sellerRequest) {
    switch (retriveLicenseType(sellerRequest)) {
      case COMPANY:
        return handleCustomerCnpj(sellerRequest.getCnpj());
      case INDIVIDUAL:
        return handleCustomerCpf(sellerRequest.getCpf());
      default:
        return StringUtils.EMPTY;
    }
  }

  private static LicenseTypeEnum retriveLicenseType(SellerRequest sellerRequest) {
    return LicenseTypeEnum.findByLiteral(sellerRequest.getLicenseType());
  }

  private static Function<SellerRequest, Seller> assemble =
      dto ->
          Seller.builder()
              .id(UUID.randomUUID().toString())
              .key(SellerRequest.generateToken())
              .documentNumber(SellerRequest.retriveDocumentNumber(dto))
              .description(SellerRequest.retriveSellerDescription(dto))
              .build();
}
