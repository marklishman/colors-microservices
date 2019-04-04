package io.lishman.green.controller.country;

import io.lishman.green.model.Country;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

final class CountryResourceAssembler extends ResourceAssemblerSupport<Country, CountryResource> {

    private CountryResourceAssembler() {
        super(CountryController.class, CountryResource.class);
    }

    public static CountryResourceAssembler getInstance() {
        return new CountryResourceAssembler();
    }

    @Override
    protected CountryResource instantiateResource(Country country) {
        return CountryResource.fromCountry(country);
    }

    @Override
    public CountryResource toResource(Country country) {
        return createResourceWithId(country.getId(), country);
    }
}
