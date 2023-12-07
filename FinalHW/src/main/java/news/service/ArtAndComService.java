package news.service;

import java.util.*;

import news.entity.Article;
import news.entity.ArticleWithoutId;
import news.entity.Comment;
import news.entity.id.ArticleId;
import news.entity.id.CommentId;
import news.repository.SQLArticleRepository;
import news.repository.SQLCommentRepository;
import news.repository.exception.ArticleIdDuplicatedException;
import news.repository.exception.ArticleNotFoundException;
import news.repository.exception.CommentNotFoundException;

public class ArtAndComService {
  private final SQLArticleRepository articleRepository;
  private final SQLCommentRepository commentRepository;

  public ArtAndComService(SQLArticleRepository articleRepository, SQLCommentRepository commentRepository) {
    this.articleRepository = articleRepository;
    this.commentRepository = commentRepository;
  }

  public List<Article> findAllArticles() {
    return articleRepository.findAll();
  }

  public Article findArticleById(ArticleId articleId) throws ArticleNotFoundException {
    return articleRepository.findById(articleId);
  }

  public ArticleId createArticle(String name, String[] tags) throws ArticleIdDuplicatedException {
    ArticleWithoutId article = new ArticleWithoutId(name, tags);

    return articleRepository.create(article);
  }

  public List<ArticleId> multiCreateArticle(Map<String, String[]> map) throws ArticleIdDuplicatedException {
    List<ArticleWithoutId> articles = new ArrayList<>();
    for (Map.Entry<String, String[]> entry : map.entrySet()) {
      ArticleWithoutId article = new ArticleWithoutId(entry.getKey(), entry.getValue());
      articles.add(article);
    }

    return articleRepository.multiCreate(articles);
  }

  public void updateArticleWithName(ArticleId articleId, String name) throws ArticleNotFoundException {
    Article article = articleRepository.findById(articleId);
    articleRepository.update(
        new Article(articleId, name, article.getTags())
    );
  }

  public void updateArticleWithTags(ArticleId articleId, String[] tags) throws ArticleNotFoundException {
    Article article = articleRepository.findById(articleId);
    articleRepository.update(
        new Article(articleId, article.getArticleName(), tags)
    );
  }

  public void deleteArticle(ArticleId articleId) throws ArticleNotFoundException {
    articleRepository.delete(articleId);
  }

  public Comment getComment(ArticleId articleId, CommentId commentId) throws ArticleNotFoundException, CommentNotFoundException {
    return commentRepository.findById(articleId, commentId);
  }

  public void deleteComment(ArticleId articleId, CommentId commentId) throws CommentNotFoundException{
    commentRepository.delete(articleId, commentId);
  }

  public CommentId createComment (ArticleId articleId, String mainBody) throws ArticleNotFoundException {
    Article article = articleRepository.findById(articleId);
    CommentId commentId = commentRepository.generateId(article);
    Comment comment = new Comment(commentId, mainBody, articleId);
    return commentRepository.create(comment);
  }
}
