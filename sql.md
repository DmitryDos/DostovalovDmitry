Запрос 1:

SELECT
	COUNT (profile.profile_id) AS Number_of_profiles 
FROM 
	profile LEFT JOIN post 
ON 
	profile.profile_id = post.profile_id 
WHERE 
	post.post_id IS NULL;


Запрос 2:

SELECT 
	post.post_id AS id
FROM 
	post LEFT JOIN comment 
ON 
	post.post_id = comment.post_id     
WHERE
	LENGTH(content) > 20 AND title ~ '^[0-9]'
GROUP BY 
	post.post_id 
HAVING 
	COUNT(comment.comment_id) = 2 ORDER BY post.post_id
LIMIT 10;


Запрос 3:

SELECT 
	post.post_id AS id
FROM 
	post LEFT JOIN comment 
ON 
	post.post_id = comment.post_id 
GROUP BY 
	post.post_id 
HAVING 
	COUNT(comment.comment_id) < 2 ORDER BY post.post_id
LIMIT 10;
