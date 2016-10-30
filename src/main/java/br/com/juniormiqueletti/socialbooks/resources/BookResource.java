package br.com.juniormiqueletti.socialbooks.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by junio on 30/10/2016.
 */
@RestController
public class BookResource {

    @RequestMapping(value = "books",method = RequestMethod.GET)
    public String list(){
        return "RestFull, Git Step-by-step";
    }
}
