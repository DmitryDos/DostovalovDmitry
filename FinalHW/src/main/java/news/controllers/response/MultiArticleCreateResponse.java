package news.controllers.response;

import news.entity.id.ArticleId;

import java.util.List;

public record MultiArticleCreateResponse(List<ArticleId> ids){

}
