package matsu_chara.quill.free

import com.typesafe.scalalogging.StrictLogging
import io.getquill.{MysqlJdbcContext, SnakeCase}
import matsu_chara.quill.free.repo.{PersonFreeRepository, PersonRepository}

object Main extends StrictLogging {
  val personRepository = new PersonRepository

  def main(args: Array[String]): Unit = {
    implicit val ctx: MysqlJdbcContext[SnakeCase] = new MysqlJdbcContext[SnakeCase]("ctx")

    try {
      normal()
    } finally {
      ctx.close()
    }
  }

  def normal()(implicit ctx: MysqlJdbcContext[SnakeCase]): Unit = {
    val personOpt = ctx.transaction {
      personRepository.deleteAll()
      personRepository.insert(Person(id = 1, state = 0))
      personRepository.findById(1)
    }
    logger.info(s"normal result = $personOpt")
  }
}
