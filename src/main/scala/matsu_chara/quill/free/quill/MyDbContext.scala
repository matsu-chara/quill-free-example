package matsu_chara.quill.free.quill

import io.getquill.{MysqlEscape, MysqlJdbcContext, SnakeCase}

class MyDbContext extends MysqlJdbcContext[SnakeCase with MysqlEscape]("ctx")
