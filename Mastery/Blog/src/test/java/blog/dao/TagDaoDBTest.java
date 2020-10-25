package blog.dao;

import blog.TestApplicationConfiguration;
import blog.dto.Tag;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author mohammedchowdhury
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class TagDaoDBTest {

    @Autowired
    TagDao tagDao;

    @BeforeEach
    @AfterEach
    public void setUp() {
        String a = "a";
        List<Tag> listOfTags = tagDao.getAllTags();
        for (Tag tag : listOfTags) {
            tagDao.deleteTag(tag.getTagID());
        }
    }

    @Test
    public void testCreateTag() {
        //arrange 
        Tag tag = new Tag();
        tag.setHashTag("#Family");
        //act 
        Tag newTag = tagDao.createTag(tag);
        tag.setTagID(newTag.getTagID());
        //assert
        assertEquals(tag, newTag);
    }

    @Test
    public void testCreateTag2() {
        //arrange 
        Tag tag = new Tag();
        tag.setHashTag("#Family");
        Tag tag2 = new Tag();
        tag2.setHashTag("#Family");
        //act 
        tag = tagDao.createTag(tag);
        String a = "AA";
        tag2 = tagDao.createTag(tag2);
        //assert
        assertEquals(tag, tag2);
    }

    @Test
    public void testgetTagById() {
        //arrange 
        Tag tag = new Tag();
        tag.setHashTag("#Family");
        //act 
        Tag newTag = tagDao.createTag(tag);
        tag = tagDao.getTagById(newTag.getTagID());
        //assert
        assertEquals(tag, newTag);
    }

    @Test
    public void testGetTagbyHashTag() {
        //arrange 
        Tag tag = new Tag();
        tag.setHashTag("#Family");
        //act 
        Tag newTag = tagDao.createTag(tag);
        tag = tagDao.getTagbyHashTag(newTag.getHashTag());
        //assert
        assertEquals(tag, newTag);
    }

    @Test
    public void testGetAllTags() {
        //arrange 
        Tag tag = new Tag();
        tag.setHashTag("#Family");

        Tag tag2 = new Tag();
        tag2.setHashTag("#Family");

        Tag tag3 = new Tag();
        tag3.setHashTag("#Food");
        //act 
        tag = tagDao.createTag(tag);
        tag2 = tagDao.createTag(tag2);
        tag3 = tagDao.createTag(tag3);

        List<Tag> allTags = tagDao.getAllTags();
        int size = 2;
        int actualSize = allTags.size();
        //assert
        assertEquals(size, actualSize);
        assertTrue(allTags.contains(tag));
        assertTrue(allTags.contains(tag2));
        assertTrue(allTags.contains(tag3));
        assertEquals(tag, tag2);
    }

    @Test
    public void testDeleteTag() {
        //arrange 
        Tag tag = new Tag();
        tag.setHashTag("#Family");

        Tag tag2 = new Tag();
        tag2.setHashTag("#Family");

        Tag tag3 = new Tag();
        tag3.setHashTag("#Food");
        //act 
        tag = tagDao.createTag(tag);
        tag2 = tagDao.createTag(tag2);
        tag3 = tagDao.createTag(tag3);

        tagDao.deleteTag(tag.getTagID());
        tagDao.deleteTag(tag2.getTagID());
        tagDao.deleteTag(tag3.getTagID());

        tag = tagDao.getTagById(tag.getTagID());
        tag2 = tagDao.getTagById(tag2.getTagID());
        tag3 = tagDao.getTagById(tag3.getTagID());

        List<Tag> allTags = tagDao.getAllTags();
        int size = 0;
        int actualSize = allTags.size();

        //assert
        assertEquals(size, actualSize);
        assertTrue(!allTags.contains(tag));
        assertTrue(!allTags.contains(tag2));
        assertTrue(!allTags.contains(tag3));
        assertNull(tag);
        assertNull(tag2);
        assertNull(tag3);
    }

    @Test
    public void testUpdateTag() {
        //arrange 
        Tag tag = new Tag();
        tag.setHashTag("#Family");

        //act 
        tag = tagDao.createTag(tag);
        tag.setHashTag("Apple");
        tagDao.updateTag(tag);

        Tag newTag = tagDao.getTagById(tag.getTagID());

        List<Tag> allTags = tagDao.getAllTags();
        int size = 1;
        int actualSize = allTags.size();

        //assert
        assertEquals(newTag, tag);
        assertEquals(size, actualSize);
        assertTrue(allTags.contains(tag));
    }

    @Test
    public void testUpdateTag2() {
        //arrange 
        Tag tag = new Tag();
        tag.setHashTag("#Family");

        Tag tag2 = new Tag();
        tag2.setHashTag("#Food");

        //act 
        tag = tagDao.createTag(tag);
        tag2 = tagDao.createTag(tag2);

        tag.setHashTag("Food");
        tagDao.updateTag(tag);
        Tag newTag = tagDao.getTagById(tag.getTagID());
        List<Tag> allTags = tagDao.getAllTags();
        int size = 2;
        int actualSize = allTags.size();
        //assert
        assertEquals(size, actualSize);
        assertTrue(allTags.contains(tag2));
        assertTrue(allTags.contains(newTag));
    }
}