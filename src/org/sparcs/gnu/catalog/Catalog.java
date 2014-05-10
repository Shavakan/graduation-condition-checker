package org.sparcs.gnu.catalog;
/**
 * 졸업 요건 조건을 갖고 있는다.
 * @author Alphamin
 *
 */
public class Catalog {
	private Replace replace;
	private Rule rule;
	private Exception exception;
	/**
	 * 생성자.
	 */
	private Catalog(){
	}
	/**
	 * 카탈로그를 생성하고 카탈로그가 들고있는 세가지 기준에 대한 설정 객체를 만든다.
	 * @param path 변환된 문법 파일 경로
	 * @return 생성된 카탈로그
	 */
	public static Catalog loadCatalog(String path){
		Catalog catalog = new Catalog();
		catalog.replace = new Replace();
		catalog.rule = new Rule();
		catalog.exception = new Exception();
		return catalog;
		
	}
}
