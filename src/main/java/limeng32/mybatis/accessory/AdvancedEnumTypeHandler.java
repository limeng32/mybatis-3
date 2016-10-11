/*
 *	本类并非mybatis原作者开发，而是由limeng32增加至mybatis源代码中，作用为增强mybatis对enum类型属性的映射效果。
 *	如有使用上的疑问请在 https://github.com/limeng32 上给作者发短消息或在作者的个人网站 limeng32.com 上留言。
 * 	祝商祺。
 */

package limeng32.mybatis.accessory;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EnumSet;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class AdvancedEnumTypeHandler<E extends Enum<E>> extends
		BaseTypeHandler<E> {

	private Class<E> type;

	public AdvancedEnumTypeHandler(Class<E> type) {
		if (type == null)
			throw new IllegalArgumentException("Type argument cannot be null");
		this.type = type;
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, E parameter,
			JdbcType jdbcType) throws SQLException {
		if (jdbcType == null) {
			ps.setString(i, ((AdvancedEnumTypeFace) parameter).flag());
		} else {
			ps.setObject(i, ((AdvancedEnumTypeFace) parameter).flag(),
					jdbcType.TYPE_CODE); // see
			// r3589
		}
	}

	@Override
	public E getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
		String s = rs.getString(columnName);
		EnumSet<E> enumSet = EnumSet.allOf(type);
		E ret = null;
		for (Object object : enumSet) {
			Enum<E> e = (Enum<E>) object;
			if (((AdvancedEnumTypeFace) e).flag().equals(s)) {
				ret = (E) e;
				break;
			}
		}
		return ret;
	}

	@Override
	public E getNullableResult(ResultSet rs, int columnIndex)
			throws SQLException {
		String s = rs.getString(columnIndex);
		EnumSet<E> enumSet = EnumSet.allOf(type);
		E ret = null;
		for (Object object : enumSet) {
			Enum<E> e = (Enum<E>) object;
			if (((AdvancedEnumTypeFace) e).flag().equals(s)) {
				ret = (E) e;
				break;
			}
		}
		return ret;
	}

	@Override
	public E getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		String s = cs.getString(columnIndex);
		EnumSet<E> enumSet = EnumSet.allOf(type);
		E ret = null;
		for (Object object : enumSet) {
			Enum<E> e = (Enum<E>) object;
			if (((AdvancedEnumTypeFace) e).flag().equals(s)) {
				ret = (E) e;
				break;
			}
		}
		return ret;
	}
}