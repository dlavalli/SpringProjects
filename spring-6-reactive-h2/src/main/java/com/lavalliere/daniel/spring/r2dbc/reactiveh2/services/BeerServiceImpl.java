package com.lavalliere.daniel.spring.r2dbc.reactiveh2.services;

import com.lavalliere.daniel.spring.r2dbc.reactiveh2.mappers.BeerMapper;
import com.lavalliere.daniel.spring.r2dbc.reactiveh2.mappers.BeerMapperImpl;
import com.lavalliere.daniel.spring.r2dbc.reactiveh2.model.BeerDTO;
import com.lavalliere.daniel.spring.r2dbc.reactiveh2.repopsitories.BeerRepository;
import org.springframework.context.annotation.Import;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Import({BeerMapperImpl.class})
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public Mono<Void> deleteBeerById(Integer beerId) {
        return beerRepository.deleteById(beerId);
    }

    @Override
    public Mono<BeerDTO> patchBeer(Integer beerId, BeerDTO beerDTO) {
        return beerRepository.findById(beerId)
            .map(foundBeer -> {
                if(StringUtils.hasText(beerDTO.getBeerName())){
                    foundBeer.setBeerName(beerDTO.getBeerName());
                }

                if(StringUtils.hasText(beerDTO.getBeerStyle())){
                    foundBeer.setBeerStyle(beerDTO.getBeerStyle());
                }

                if(beerDTO.getPrice() != null){
                    foundBeer.setPrice(beerDTO.getPrice());
                }

                if(StringUtils.hasText(beerDTO.getUpc())){
                    foundBeer.setUpc(beerDTO.getUpc());
                }

                if(beerDTO.getQuantityOnHand() != null){
                    foundBeer.setQuantityOnHand(beerDTO.getQuantityOnHand());
                }
                return foundBeer;
            }).flatMap(beerRepository::save)
            .map(beerMapper::beerToBeerDto);
    }

    @Override
    public Mono<BeerDTO> updateBeer(
        Integer beerId,
        BeerDTO beerDTO
    ) {
        return beerRepository.findById(beerId)
            .map(foundBeer -> {
                //update properties
                foundBeer.setBeerName(beerDTO.getBeerName());
                foundBeer.setBeerStyle(beerDTO.getBeerStyle());
                foundBeer.setPrice(beerDTO.getPrice());
                foundBeer.setUpc(beerDTO.getUpc());
                foundBeer.setQuantityOnHand(beerDTO.getQuantityOnHand());

                return foundBeer;
            }).flatMap(beerRepository::save)
              .map(beerMapper::beerToBeerDto);
    }

    @Override
    public Mono<BeerDTO> getBeerById(Integer beerId) {
        return beerRepository.findById(beerId)
            .map(beerMapper::beerToBeerDto);
    }

    @Override
    public Flux<BeerDTO> listBeers() {
        return beerRepository.findAll()
            .map(beerMapper::beerToBeerDto);
    }

    @Override
    public Mono<BeerDTO> saveNewBeer(BeerDTO beerDTO) {
        return beerRepository.save(beerMapper.beerDtoToBeer(beerDTO))
            .map(beerMapper::beerToBeerDto);
    }
}