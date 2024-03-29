package com.lucasbarbosa.libraryapi.feign.restcountryapi;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/** @author Lucas Barbosa on 31/07/2021 */
@FeignClient(name = "country", url = "${feign.client.country.url}")
public interface CountryClient {

  @GetMapping("/{code}")
  List<CountryVO> findCountryByInitial(@PathVariable(value = "code") String countryCode);
}
