package news.controllers.response;

import news.entity.Article;

import java.util.List;

public record ArticleGetAllResponse (List <Article> articles){

}
