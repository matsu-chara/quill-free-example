package matsu_chara.quill_free.quill

import io.getquill.{MysqlEscape, MysqlJdbcContext, SnakeCase}

class MyDbContext extends MysqlJdbcContext[SnakeCase with MysqlEscape]("ctx")
