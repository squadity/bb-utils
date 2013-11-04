package net.bolbat.utils.lang;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

/**
 * {@link ToStringUtils} test.
 * 
 * @author Alexandr Bolbat
 */
public class ToStringUtilsTest {

	/**
	 * Complex utility test.
	 */
	@Test
	public void complexTest() {
		Assert.assertEquals("[]", ToStringUtils.toString(null));
		Assert.assertEquals("[1]", ToStringUtils.toString(Arrays.asList(1)));
		Assert.assertEquals("[1,2,3,4,5]", ToStringUtils.toString(Arrays.asList(1, 2, 3, 4, 5)));
		Assert.assertEquals("[1,2,3,4,5,6,7,8,9,10,...]", ToStringUtils.toString(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)));
		Assert.assertEquals("[]", ToStringUtils.toString(null, 0));
		Assert.assertEquals("[...]", ToStringUtils.toString(Arrays.asList(1, 2, 3, 4, 5), 0));
		Assert.assertEquals("[1,2]", ToStringUtils.toString(Arrays.asList(1, 2), 5));
		Assert.assertEquals("[1,2,3,4,5,...]", ToStringUtils.toString(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12), 5));
	}

}
