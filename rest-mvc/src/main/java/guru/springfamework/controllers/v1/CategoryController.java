package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.api.v1.model.CatorgoryListDTO;
import guru.springfamework.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

// @Controller
/*
  With @RestController
  We're no longer returning back that Response Entity. So now we have a RestController which is just a recap
  a convenience method, to say that we're returning back a ResponseBody from these. So the @ResponseBody annotation
  says I'm gonna return back an object but I want you to parse it to the proper implementation where the clients asking
  for JSON or XML or whatever, the Framework is gonna handle that based on the request type and handling that object.
 */

@RestController
@RequestMapping(CategoryController.BASE_URL)
public class CategoryController {

    public static final String BASE_URL = "/api/v1/categories";

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /*
    // Requires @Controller
    @GetMapping
    public ResponseEntity<CatorgoryListDTO> getallCatetories(){
        return new ResponseEntity<CatorgoryListDTO>(
                new CatorgoryListDTO(categoryService.getAllCategories()), HttpStatus.OK);
    }
     */

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CatorgoryListDTO getallCatetories() {
       return new CatorgoryListDTO(categoryService.getAllCategories());
    }

    /*
    // Requires @Controller
    @GetMapping("{name}")
    public ResponseEntity<CategoryDTO> getCategoryByName( @PathVariable String name){
        return new ResponseEntity<CategoryDTO>(
                categoryService.getCategoryByName(name), HttpStatus.OK
        );
    }
     */

    @GetMapping("{name}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDTO getCategoryByName( @PathVariable String name) {
        return categoryService.getCategoryByName(name);
    }
}